package lesson4;

import lesson4.EquipmentItem;
import lombok.Data;

import java.util.List;

@Data
public class EquipmentResponse {
    private List<EquipmentItem> equipment;
}
