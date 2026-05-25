package ayd2.ps2026.demo.constants.enums;

public enum ConstantsEnum {

    COMMISSION_PERCENTAGE(100);

    private final Integer id;

    private ConstantsEnum(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return this.id;
    }

}
