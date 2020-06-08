package com.microsoft.azure.datasource2;

import com.microsoft.azure.spring.data.cosmosdb.core.mapping.Document;
import com.microsoft.azure.spring.data.cosmosdb.core.mapping.PartitionKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Document(collection = "users-collection-alias")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    private String id;

    private String email;

    @PartitionKey
    private String name;

    private String address;

    public String toString() {
        return String.format("%s: %s %s %s", this.id, this.email, this.name, this.address);
    }
}
