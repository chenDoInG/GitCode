package com.chendoing.gitcode.data.api.model.payload;

import com.chendoing.gitcode.R;
import com.chendoing.gitcode.data.api.model.Deployment;
import com.chendoing.gitcode.data.api.model.DeploymentStatus;

public class DeploymentStatusEvent extends Payloaded {

    private DeploymentStatus deployment_status;
    private Deployment deployment;

    public void setDeployment_status(DeploymentStatus deployment_status) {
        this.deployment_status = deployment_status;
    }

    public DeploymentStatus getDeployment_status() {
        return deployment_status;
    }

    public Deployment getDeployment() {
        return deployment;
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }

    @Override
    public String toString() {
        return "DeploymentStatusPayload{" +
                "deployment_status=" + deployment_status +
                ", deployment=" + deployment +
                '}';
    }

    @Override
    public int getDrawable() {
        return R.drawable.mega_icon_milestone;
    }
}
