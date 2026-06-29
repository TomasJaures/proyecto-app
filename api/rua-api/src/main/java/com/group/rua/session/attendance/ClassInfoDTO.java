package com.group.rua.session.attendance;

// Importa el Enum de tu entidad Block para que coincida el tipo de dato
import com.group.rua.entities.Block.BlockState;

public class ClassInfoDTO {
    private Integer blockId;
    private BlockState blockState; // <-- ENUM
    private Integer classId;
    private Boolean isAnulled;
    private Integer num;
    private String subjectName;
    private String code;

    // EL CONSTRUCTOR EXIGIDO POR SPRING:
    public ClassInfoDTO(Integer blockId, BlockState blockState, Integer classId, Boolean isAnulled, Integer num, String subjectName, String code) {
        this.blockId = blockId;
        this.blockState = blockState;
        this.classId = classId;
        this.isAnulled = isAnulled;
        this.num = num;
        this.subjectName = subjectName;
        this.code = code;
    }

    // Getters y Setters...

    public Integer getBlockId() {
        return blockId;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public Integer getClassId() {
        return classId;
    }

    public String getCode() {
        return code;
    }

    public Boolean getIsAnulled() {
        return isAnulled;
    }

    public Integer getNum() {
        return num;
    }

    public String getSubjectName() {
        return subjectName;
    }
}