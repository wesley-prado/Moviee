package com.codemages.Moviee.entities;

public enum DocumentType {
	CPF, CNPJ, RG;

	public static DocumentType fromString(String type) {
		for (DocumentType docType : DocumentType.values()) {
			if (docType.name().equalsIgnoreCase(type)) {
				return docType;
			}
		}
		throw new IllegalArgumentException("Unknown document type: " + type);
	}
}
