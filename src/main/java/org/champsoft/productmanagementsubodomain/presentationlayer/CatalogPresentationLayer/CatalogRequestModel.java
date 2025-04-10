package org.champsoft.productmanagementsubodomain.presentationlayer.CatalogPresentationLayer;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CatalogRequestModel {

    private String catalogId;
    private String type;
    private String description;
}
