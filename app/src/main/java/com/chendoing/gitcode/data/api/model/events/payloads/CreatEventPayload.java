package com.chendoing.gitcode.data.api.model.events.payloads;

/**
 * Created by chenDoInG on 16/5/26.
 */
public class CreatEventPayload {

    private String ref_type;
    private String ref;
    private String master_branch;
    private String description;

    @Override
    public String toString() {
        return "CreatEventPayload{" +
                "ref_type='" + ref_type + '\'' +
                ", ref='" + ref + '\'' +
                ", master_branch='" + master_branch + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getRef_type() {
        return ref_type;
    }

    public void setRef_type(String ref_type) {
        this.ref_type = ref_type;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getMaster_branch() {
        return master_branch;
    }

    public void setMaster_branch(String master_branch) {
        this.master_branch = master_branch;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
