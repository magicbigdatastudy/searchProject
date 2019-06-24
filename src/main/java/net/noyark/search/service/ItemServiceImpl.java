package net.noyark.search.service;

import net.noyark.search.entity.Item;
import net.noyark.search.mapper.ItemMapper;


import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
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

}
