package com.group.rua.session.program;

import com.group.rua.entities.Module;
import java.util.List;

public class ProgramInfoDTO {
    public Integer subjectId;
    public String subjectName;
    public String code;
    public List<Module> modules;

    public ProgramInfoDTO(Integer subjectId, String subjectName, String code, List<Module> modules) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.code = code;
        this.modules = modules;
    }
}