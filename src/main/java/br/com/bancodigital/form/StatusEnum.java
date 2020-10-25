package br.com.bancodigital.form;

public enum StatusEnum {

	 ANALYSIS("ANALYSIS"), ACCEPTED("ACCEPTED"), REJECTED("REJECTED");

    private String description;

    StatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
