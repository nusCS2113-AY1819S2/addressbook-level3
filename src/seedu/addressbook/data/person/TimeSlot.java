package seedu.addressbook.data.person;

public class TimeSlot {

    private Module module;

    private String slotperiod;

    public TimeSlot(Module module,String slotperiod){
           this.module = module;
           this.slotperiod = slotperiod;
    }

    public Module getSlotModule(){
        return this.module;
    }

    public String getSlotPeriod(){
        return this.slotperiod;
    }
}
