package it.avlp.simog.beans;

public class SchedaState{
	private Integer state;
	private Long id;
	private boolean richAnn;
	private boolean richDelete;
	
	public SchedaState(Integer state, boolean richAnn, Long id) {
		this.state = state;
		this.richAnn = richAnn;
		this.id = id;
	}
	public SchedaState(Integer state, Long id) {
		this.state = state;
		this.id = id;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isRichAnn() {
		return richAnn;
	}
	public void setRichAnn(boolean richAnn) {
		this.richAnn = richAnn;
	}
	public boolean isRichDelete() {
		return richDelete;
	}
	public void setRichDelete(boolean richDelete) {
		this.richDelete = richDelete;
	}
}
