package lesson4;

import lombok.Data;

@Data
public class ConvertResponse {
    private double sourceAmount;
    private String sourceUnit;
    private double targetAmount;
    private String targetUnit;
    private String answer;
}
