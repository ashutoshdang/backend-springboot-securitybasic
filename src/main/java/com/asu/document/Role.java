package com.asu.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Role {
	
	    @Id
	    private String id;
	    
	    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
	    private String role;

}
