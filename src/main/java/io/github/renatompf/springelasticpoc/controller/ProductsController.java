package io.github.renatompf.springelasticpoc.controller;

import io.github.renatompf.springelasticpoc.dto.ProductCreateDTO;
import io.github.renatompf.springelasticpoc.model.ProductDocument;
import io.github.renatompf.springelasticpoc.model.ProductEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * REST controller exposing CRUD operations for {@link ProductEntity}.
 */
@Tag(name = "Products Controller", description = "Endpoints for managing products")
public interface ProductsController {


    @Operation(
            summary = "Save a new product",
            description = "Persists a new product in the DB",
            requestBody = @RequestBody(
                    description = "Product creation request",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ProductCreateDTO.class),
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Successful Product Creation",
                                    summary = "A sample request to create a new product",
                                    value = """
                                            {
                                              "name": "iPhone 15 Pro",
                                              "description": "Smartphone Apple topo de gama",
                                              "category": "Smartphones",
                                              "brand": "Apple",
                                              "price": 1499.99,
                                              "tags": [
                                                "iphone",
                                                "apple",
                                                "smartphone"
                                              ]
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Product successfully saved"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid Request. The request body is malformed or contains invalid data"
            )
    })
    public ResponseEntity<?> saveProduct(ProductCreateDTO product);

    @Operation(
            summary = "Get all products",
            description = "Retrieves a paginated list of all products retrieved from PostgresDB"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of products"
            )
    })
    public ResponseEntity<Page<ProductEntity>> getAll(Pageable pageable);

    @Operation(
            summary = "Search products by name or description",
            description = """
                    Retrieves a paginated list of products matching the given query.
                    This search looks for matches in both the name, description and tags fields of the products.
                    This search is made using Elasticsearch.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of products"
            )
    })
    public ResponseEntity<Page<ProductDocument>> getByNameAndDescription(String query, Pageable pageable);

    @Operation(
            summary = "Delete a product by ID",
            description = "Deletes the product with the specified ID from both Postgres and ElasticSearch."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Product successfully deleted"
            )
    })
    public ResponseEntity<?> deleteById(
            @Parameter(description = "ID of the product", required = true)
            UUID id
    );


}