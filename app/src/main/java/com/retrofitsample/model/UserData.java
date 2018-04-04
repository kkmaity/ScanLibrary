package com.retrofitsample.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kamal on 03/10/2018.
 */

public class UserData {
    @SerializedName("agentid")
    @Expose
    private String agentid;
    @SerializedName("agent")
    @Expose
    private String agent;
    @SerializedName("activ")
    @Expose
    private String activ;
    @SerializedName("idEmployee")
    @Expose
    private String idEmployee;
    @SerializedName("FirebaseKey")
    @Expose
    private Object firebaseKey;
    @SerializedName("ProfilFoto")
    @Expose
    private Object profilFoto;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("montator")
    @Expose
    private String montator;
    @SerializedName("locatie")
    @Expose
    private String locatie;

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getActiv() {
        return activ;
    }

    public void setActiv(String activ) {
        this.activ = activ;
    }

    public String getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Object getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(Object firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public Object getProfilFoto() {
        return profilFoto;
    }

    public void setProfilFoto(Object profilFoto) {
        this.profilFoto = profilFoto;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMontator() {
        return montator;
    }

    public void setMontator(String montator) {
        this.montator = montator;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

}
