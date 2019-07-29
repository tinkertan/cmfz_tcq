package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "cmfz",type = "article")
public class Article implements Serializable {
    @Id
    private String id;
    @Field(type = FieldType.Keyword)
    private String title;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Field(type = FieldType.Date)
    private Date publishTime;
    @Field(type = FieldType.Keyword)
    private String guruId;
    @Field(type = FieldType.Keyword)
    private String guruName;
}
