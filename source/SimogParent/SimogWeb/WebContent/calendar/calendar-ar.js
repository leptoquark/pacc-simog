// ** I18N - Arabic Calendar
//
// Calendar Arabic language
// Author: Mihai Bazon, <mihai_bazon@yahoo.com>
// Translator: SIMOG Team
// Encoding: UTF-8
// Distributed under the same terms as the calendar itself.

// full day names (Arabic)
Calendar._DN = new Array
("الأحد",
 "الإثنين",
 "الثلاثاء",
 "الأربعاء",
 "الخميس",
 "الجمعة",
 "السبت",
 "الأحد");

// short day names (Arabic)
Calendar._SDN = new Array
("أحد",
 "إثن",
 "ثلث",
 "أرب",
 "خمس",
 "جمع",
 "سبت",
 "أحد");

Calendar._FD = 6; // Friday is first day of week in Arabic calendar

// full month names (Arabic)
Calendar._MN = new Array
("يناير",
 "فبراير",
 "مارس",
 "أبريل",
 "مايو",
 "يونيو",
 "يوليو",
 "أغسطس",
 "سبتمبر",
 "أكتوبر",
 "نوفمبر",
 "ديسمبر");

// short month names (Arabic)
Calendar._SMN = new Array
("ينا",
 "فبر",
 "مار",
 "أبر",
 "ماي",
 "يون",
 "يول",
 "أغس",
 "سبت",
 "أكت",
 "نوف",
 "ديس");

// tooltips (Arabic)
Calendar._TT = {};
Calendar._TT["INFO"] = "مسح حقل التاريخ";

Calendar._TT["ABOUT"]= "محدد التاريخ/الوقت DHTML\n" +
"(c) dynarch.com 2002-2005 / Author: Mihai Bazon\n" + // don't translate this
"للحصول على التحديثات: http://www.dynarch.com/projects/calendar/\n" +
"موزع تحت رخصة GNU LGPL. راجع http://gnu.org/licenses/lgpl.html للتفاصيل." +
"\n\n" +
"اختيار التاريخ:\n" +
"- استخدم \xab, \xbb لاختيار السنة\n" +
"- استخدم  " + String.fromCharCode(0x2039) + ", " + String.fromCharCode(0x203a) + " للأشهر\n" +
"- اضغط مع الاستمرار للوصول إلى وظائف الاختيار السريع.";

Calendar._TT["ABOUT_TIME"] = "\n\n" +
"اختيار الوقت:\n" +
"- انقر على الرقم لزيادته\n" +
"- أو Shift+انقر لتقليله\n" +
"- أو انقر واسحب لليسار أو لليمين لتغييره.";

Calendar._TT["PREV_YEAR"] = "السنة السابقة (انقر مع الاستمرار للقائمة)";
Calendar._TT["PREV_MONTH"] = "الشهر السابق (انقر مع الاستمرار للقائمة)";
Calendar._TT["GO_TODAY"] = "اليوم";
Calendar._TT["NEXT_MONTH"] = "الشهر التالي (انقر مع الاستمرار للقائمة)";
Calendar._TT["NEXT_YEAR"] = "السنة التالية (انقر مع الاستمرار للقائمة)";
Calendar._TT["SEL_DATE"] = "اختر التاريخ";
Calendar._TT["DRAG_TO_MOVE"] = "اسحب للتحريك";
Calendar._TT["PART_TODAY"] = " (اليوم)";

// the following is to inform that "%s" is to be the first day of week
// %s will be replaced with the day name.
Calendar._TT["DAY_FIRST"] = "اعرض أولاً %s";

// This may be locale-dependent.  It specifies the week-end days, as an array
// of comma-separated numbers.  The numbers are from 0 to 6: 0 means Sunday, 1
// means Monday, etc.
// In Arabic countries, weekend is typically Friday and Saturday
Calendar._TT["WEEKEND"] = "5,6";

Calendar._TT["CLOSE"] = "إغلاق";
Calendar._TT["TODAY"] = "اليوم";
Calendar._TT["TIME_PART"] = "(Shift-)انقر أو اسحب لتغيير القيمة";

// date formats
Calendar._TT["DEF_DATE_FORMAT"] = "%d-%m-%Y";
Calendar._TT["TT_DATE_FORMAT"] = "%a %e %b";

Calendar._TT["WK"] = "أسبوع";
Calendar._TT["TIME"] = "الوقت:";

