package com.asu.document;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection ="profilePhotos")
@AllArgsConstructor
public class ProfilePhoto {

	@Id
    private String id;
     
    private String title;
         
    private Binary image;
    

	public ProfilePhoto(String title2) {
		this.title=title2;
	}

}
