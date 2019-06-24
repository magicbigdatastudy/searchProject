package net.noyark.search.service;

import net.noyark.search.entity.Item;
import net.noyark.search.mapper.ItemMapper;


import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemMapper mapper;

    public void createIndex() throws Exception{
        List<Item> list = mapper.findAll();

        Path path = Paths.get("item.dir");

        Directory directory = FSDirectory.open(path);

        IndexWriterConfig config = new IndexWriterConfig();

        IndexWriter writer = new IndexWriter(directory,config);

        for(Item item:list) {
            Document doc = new Document();
            doc.add(new StringField("id",item.getId()+"", Field.Store.YES));
            doc.add(new StringField("price",item.getPrice()+"", Field.Store.YES));
            doc.add(new TextField("title",item.getTitle(), Field.Store.YES));
            doc.add(new StringField("image",item.getImage(), Field.Store.YES));
            writer.addDocument(doc);
        }
        writer.commit();
        writer.close();
        directory.close();
    }

    @Override
    public List<Item> search(String title, String value) throws Exception {
        Path path = Paths.get("indexItem");
        Directory directory = FSDirectory.open(path);
        IndexReader reader = DirectoryReader.open(directory);

        IndexSearcher searcher = new IndexSearcher(reader);
        Term term = new Term(title,value);

        Query query = new TermQuery(term);

        TopDocs topDoc = searcher.search(query,10);
        ScoreDoc[] scoreDocs = topDoc.scoreDocs;
        List<Item> items = new ArrayList<>();
        for(ScoreDoc doc:scoreDocs){
            Document document = searcher.doc(doc.doc);
            Item item = new Item();
            item.setId(Long.parseLong(document.get("id")));
            item.setPrice(Long.parseLong(document.get("price")));
            item.setTitle(document.get("title"));
            item.setImage(document.get("image"));
            item.setSellPoint(document.get("sellPoint"));
            items.add(item);
        }
        return items;
    }
}
