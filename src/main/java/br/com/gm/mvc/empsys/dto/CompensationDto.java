package br.com.gm.mvc.empsys.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.com.gm.mvc.empsys.model.Compensation;
import br.com.gm.mvc.empsys.model.CompensationType;

public class CompensationDto {
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    private String type;
    private String amount;
    private String description;
    private String date;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amout) {
        this.amount = amout;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public Compensation toCompensation() {
        
        Compensation compensation = new Compensation();
        
        compensation.setType(CompensationType.valueOf(this.getType()));
        
        compensation.setAmount(Double.valueOf(this.getAmount()));
        
        compensation.setDescription(this.getDescription());
        
        compensation.setDate(LocalDate.parse(this.date, formatter));
        
        return compensation;
        
    }

    @Override
    public String toString() {
        return "CompensationDto [type=" + type + ", amount=" + amount + ", description=" + description + ", date="
                + date + "]";
    }
    
}
