package com.br.bibliotech.controllers.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.br.bibliotech.data.dto.request.EmailRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface EmailControllerDocs {

  // para envio de e-mails
  @Operation(summary = "Enviando o e-Mail", description = "Enviando o e-Mail com os devidos detalhes,campos e corpo", tags = {
      "e=Mail" }, responses = {
          @ApiResponse(description = "Success", responseCode = "200", content = @Content),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
      })
  ResponseEntity<String> sendEmail(EmailRequestDto emailRequestDto);

  @Operation(summary = "Enviando o e-Mail com Attachment", description = "Enviando o e-Mail com Attachment com os devidos detalhes,campos e corpo", tags = {
      "e=Mail" }, responses = {
          @ApiResponse(description = "Success", responseCode = "200", content = @Content),
          @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
          @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
          @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
      })
  ResponseEntity<String> sendEmailWithAttachment(String emailRequestJson, MultipartFile multipartFile);

}
