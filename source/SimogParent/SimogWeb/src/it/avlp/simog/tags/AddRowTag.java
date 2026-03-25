package it.avlp.simog.tags;

import java.io.PrintStream;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class AddRowTag extends TagSupport {
   
   private static final long serialVersionUID = -2909124099395051011L;
   
   private String prefix;
   private String headers;
   private String fields;
   private String hiddenFields;
   private String width = "99%";
   private String heigth = "150px";
   private Iterable rows;
   private boolean readonly;
   
   private AddRowTag(String prefix, String headers, String fields, String hiddenFields, boolean readonly) {
      this.prefix = prefix;
      this.headers = headers;
      this.fields = fields;
      this.hiddenFields = hiddenFields;
      this.readonly = readonly;
   }

   @Override
   public int doStartTag() throws JspException {
      //JspWriter out = pageContext.getOut();
      PrintStream out = System.out;
      try
      {
         out.println("<div id=\"DIVTabella" + prefix + "\" class=\"scrollTabs\" style=\"height: " + heigth + "; width: " + width + "; display: block;\">");
         out.println("<table id=\"idTabella" + prefix + "\" width=\"100%\" >");
         out.println("<tbody>");
         out.println("<tr>");
         if(!readonly)out.println("<th class=\"garaTh\" width=\"125\">Azione</th>");
         for(String header: headers.split(",")){
            out.println("<th class=\"garaTh\">" + header + "</th>");
         }
         out.println("</tr>");

         return super.doStartTag();
      }
      catch (Throwable e){
         e.printStackTrace();
         throw new JspException(e);
      }
   }

   @Override
   public int doEndTag() throws JspException {
      //JspWriter out = pageContext.getOut();
      PrintStream out = System.out;
      try
      {
         
         return super.doEndTag();
      }
      catch (Throwable e){
         e.printStackTrace();
         throw new JspException(e);
      }
   }

   

   public static void main(String[] args) {
      try 
      {
         
         AddRowTag tag = new AddRowTag("CUP", "CUP,OK_UTENTE,VALIDO", "", "", false);
         tag.doStartTag();
      }
      catch (Throwable e){
         e.printStackTrace();
      }
   }
   
}
