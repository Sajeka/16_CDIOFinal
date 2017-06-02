package rest;

public class User {

	@Override
	public String toString() {
		return "UserDTO [opr_cpr=" + opr_cpr + ", opr_navn=" + opr_navn + ", ini=" + ini + ", rolle_id=" + rolle_id + "]";
	}

	private String opr_cpr;                     
	private String opr_navn;                
	private String ini;      
	private String password;
	private int rolle_id;
	
	public User() {
	}
	
	public User(String opr_cpr, String opr_navn, String ini, String password, int rolle_id) {
		this.opr_cpr = opr_cpr;
		this.opr_navn = opr_navn;
		this.ini = ini;
		this.password = password;
		this.rolle_id = rolle_id;
	}
	public String getOprCpr() {
		return opr_cpr;
	}
	
	public void setOprCpr(String opr_cpr) {
		this.opr_cpr = opr_cpr;
	}
	
	public String getOprNavn() {
		return opr_navn;
	}
	
	public void setOprNavn(String opr_navn) {
		this.opr_navn = opr_navn;
	}
	
	public String getIni() {
		return ini;
	}
	
	public void setIni(String ini) {
		this.ini = ini;
	}

	public int getRolleId() {
		return this.rolle_id;
	}

	public void setRolleId(int rolle_id) {
		this.rolle_id = rolle_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
