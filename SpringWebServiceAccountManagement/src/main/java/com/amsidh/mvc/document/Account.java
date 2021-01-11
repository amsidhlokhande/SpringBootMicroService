package com.amsidh.mvc.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Document("Accounts")
public class Account {
    @Id
    private String accountId;
    private String name;
}
