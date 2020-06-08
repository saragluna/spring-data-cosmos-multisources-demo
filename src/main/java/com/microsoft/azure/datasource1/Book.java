package com.microsoft.azure.datasource1;

import com.microsoft.azure.spring.data.cosmosdb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String isbn;

    private String name;

    private String author;

    @Override
    public String toString() {
        return String.format("%s, %s, %s", this.name, this.author, this.isbn);
    }
}
