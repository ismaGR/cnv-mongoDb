package fr.gustaveroussy.odinCNV.CopyNumberVariationMongoDB.model.embedded;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Field;

public class Comment {

    @Field(value = "author")
    private String author;

    @Field(value = "date")
    private Date date;

    @Field(value = "content")
    private String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment(String author, Date date, String content) {
        super();
        this.author = author;
        this.date = date;
        this.content = content;
    }

    public Comment() {
        super();
    }

}
