package it.avlp.simog.db.generated;

public interface INIZIATIVE_CATEGORIE {

    public final static String TABLE_NAME = "INIZIATIVE_CATEGORIE";
	
	public final static Boolean IDENTITY = Boolean.TRUE;
	
	public final static String ID_INIZIATIVE_CATEGORIE = "ID_INIZIATIVE_CATEGORIE";
	public final static String T_ID_INIZIATIVE_CATEGORIE = TABLE_NAME+"."+ID_INIZIATIVE_CATEGORIE;
	
	public long getIdIniziativeCategorie();
	public void setIdIniziativeCategorie(long id);
	
	public final static String ID_INIZIATIVA = "ID_INIZIATIVA";
	public final static String T_ID_INIZIATIVA = TABLE_NAME+"."+ID_INIZIATIVA;
	
	public long getIdIniziativa();
	public void setIdIniziativa(long id);
	
	public final static String COD_CATEGORIA = "COD_CATEGORIA";
	public final static String T_COD_CATEGORIA = TABLE_NAME+"."+COD_CATEGORIA;
	
	public long getCodCategoria();
	public void setCodCategoria(long cod);
	
}
