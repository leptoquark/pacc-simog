package it.avlp.simog.util;

import java.io.FileReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import com.csvreader.CsvReader;

import it.avlp.simog.db.Costanti;

public class RequisitiConfigFile {

	public class ReqConfig {

		public ReqConfig(BigDecimal importoDa, BigDecimal importoA, String dataWarn, String dataObbl) {
			this.importoDa = importoDa;
			this.importoA = importoA;
			this.dataWarn = dataWarn;
			this.dataObbl = dataObbl;
		}

		private BigDecimal importoDa;
		private BigDecimal importoA;
		private String dataWarn;
		private String dataObbl;

		public BigDecimal getImportoDa() {
			return importoDa;
		}

		public void setImportoDa(BigDecimal importoDa) {
			this.importoDa = importoDa;
		}

		public BigDecimal getImportoA() {
			return importoA;
		}

		public void setImportoA(BigDecimal importoA) {
			this.importoA = importoA;
		}

		public String getDataWarn() {
			return this.dataWarn;
		}

		public void setDataWarn(String dataWarn) {
			this.dataWarn = dataWarn;
		}

		public String getDataObbl() {
			return this.dataObbl;
		}

		public void setDataObbl(String dataObbl) {
			this.dataObbl = dataObbl;
		}

		@Override
		public String toString() {
			return "\nImportoDa: " + importoDa.toString() + " ImportoA: " + importoA.toString() + " DataWarn: "
					+ dataWarn + " DataObbl: " + dataObbl;
		}

		public boolean isWarning(BigDecimal importo, Date dataSistema) throws ParseException {
			return ((importo.compareTo(importoDa) >= 0 && importo.compareTo(importoA) <= 0)
					|| importo.floatValue() == Costanti.IMPORTO_FUORI_SCALA)
					&& dataSistema.compareTo(getDate(dataWarn)) >= 0;
		}

		public boolean isError(BigDecimal importo, Date dataSistema) throws ParseException {
			return ((importo.compareTo(importoDa) >= 0 && importo.compareTo(importoA) <= 0)
					|| importo.floatValue() == Costanti.IMPORTO_FUORI_SCALA)
					&& dataSistema.compareTo(getDate(dataObbl)) >= 0;
		}

		public boolean isRequisito(BigDecimal importo, Date dataSistema) throws ParseException {
			return importo.compareTo(importoDa) >= 0 && importo.compareTo(importoA) <= 0
			// && importo.floatValue() == Costanti.IMPORTO_FUORI_SCALA
					&& dataSistema.compareTo(getDate(dataObbl)) >= 0;
		}

		private Date getDate(String source) throws ParseException {
			try {
				return new SimpleDateFormat("yyyyMMdd").parse(source);
			} catch (ParseException pe) {
				pe.printStackTrace();
				throw pe;
			}
		}
	}

	private String fileName = null;
	private LinkedList<ReqConfig> reqConfigList = new LinkedList<ReqConfig>();

	public String getFileName() {
		return fileName;
	}

	public LinkedList<ReqConfig> getReqConfigList() {
		return reqConfigList;
	}

	public RequisitiConfigFile(String fileName) {
		this.fileName = fileName;
		loadCSV();
	}

	private void loadCSV() {
		CsvReader csvReader;
		try {
			csvReader = new CsvReader(new FileReader(fileName));

			csvReader.setDelimiter(';');
			csvReader.readHeaders();

			while (csvReader.readRecord()) {
				BigDecimal impDa = new BigDecimal(csvReader.get("IMPORTO_DA"));
				BigDecimal impA = new BigDecimal(csvReader.get("IMPORTO_A"));
				String dataO = csvReader.get("DATA_OBBL");
				String dataW = csvReader.get("DATA_WARN");

				this.reqConfigList.add(new ReqConfig(impDa, impA, dataW, dataO));
			}

			csvReader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public String toString() {
		return " fileName: " + fileName + " reqConfigList: " + reqConfigList.toString();
	}

	public static void main(String[] args) {
		// Ticket #20058 - 09 - 02 - 21
//		RequisitiConfigFile mia = new RequisitiConfigFile("C:\\Users\\Fe.Lattanzi\\Documents\\SVN\\Configurazioni\\req_config.csv");
		RequisitiConfigFile mia = new RequisitiConfigFile("/opt/SIMOG/req_config.csv");
		System.out.println(mia);
	}
}
