B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=11.5
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: false
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private et_tozih As EditText
	
	Private lbl_khoroj_time As Label
	Private lbl_vorod_time As Label
	Private cusListV_data As CustomListView
	Private lbl_show_allTime As Label
	
	Private xui As XUI
	Dim p As B4XView
	Private lbl_date_item As Label
	Private lbl_vorod_item As Label
	Private lbl_khoroj_item As Label
	Private lbl_tozih_item As Label
	
	
	
	Private lbl_edit_item As Label
	Private lbl_show_time As Label
	Private pan_all_edit As Panel
	Private et_date_edit As EditText
	Private et_tozih_edit As EditText
	Private lbl_khoroj_time_edit As Label
	Private lbl_vorod_time_edit As Label
	Private lbl_show_time_edit As Label
	
	Dim current_id As Int=0
	
	Dim dialog_tim As TimeDialog
	
	
	Private lbl_day_name As Label
	Private sp_mah As Spinner
	Private et_sall As EditText
	Private lbl_del_item As Label
	Private sp_date_day As Spinner
	Private sp_date_year As Spinner
	Private sp_date_moon As Spinner
	
	Dim date_str As String=""
	Dim str_html As StringBuilder
	Dim FileName As String=""
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("add_layout")
	
	
	
	sp_date_year.Padding=Array As Int(4dip,4dip,25dip,4dip)
	sp_date_year.Add("1400")
	sp_date_year.Add("1401")
	sp_date_year.Add("1402")
	sp_date_year.Add("1403")
	sp_date_year.Add("1404")
	sp_date_year.Add("1405")
	sp_date_year.Add("1406")
	sp_date_year.Add("1407")
	sp_date_year.Add("1408")
	sp_date_year.Add("1409")
	sp_date_year.Add("1410")
	
	
	sp_date_moon.Padding=Array As Int(4dip,4dip,25dip,4dip)
	sp_date_moon.Add("فروردین")
	sp_date_moon.Add("اردیبهشت")
	sp_date_moon.Add("خرداد")
	sp_date_moon.Add("تیر")
	sp_date_moon.Add("مرداد")
	sp_date_moon.Add("شهریور")
	sp_date_moon.Add("مهر")
	sp_date_moon.Add("آبان")
	sp_date_moon.Add("آذر")
	sp_date_moon.Add("دی")
	sp_date_moon.Add("بهمن")
	sp_date_moon.Add("اسفند")
	
	sp_date_day.Padding=Array As Int(4dip,4dip,25dip,4dip)
	sp_date_day.AddAll(Array As String("01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"))
	
	
	
	
	
	sp_date_year.SelectedIndex=1
	sp_date_moon.SelectedIndex=Main.prsianDate.PersianMonth-1
	sp_date_day.SelectedIndex=Main.prsianDate.PersianDay-1
	date_str=Main.prsianDate.PersianShortDate
	
	

	
	
	sp_mah.Padding=Array As Int(4dip,4dip,25dip,4dip)
	sp_mah.Add("فروردین")
	sp_mah.Add("اردیبهشت")
	sp_mah.Add("خرداد")
	sp_mah.Add("تیر")
	sp_mah.Add("مرداد")
	sp_mah.Add("شهریور")
	sp_mah.Add("مهر")
	sp_mah.Add("آبان")
	sp_mah.Add("آذر")
	sp_mah.Add("دی")
	sp_mah.Add("بهمن")
	sp_mah.Add("اسفند")



	et_sall.Text=Main.prsianDate.PersianYear
	sp_mah.SelectedIndex=Main.prsianDate.PersianMonth-1


	fill_list
	
	
	dialog_tim.Is24Hours=True
	
	If(File.Exists(File.DirInternal,"save_temp"))Then
		
		Try
			Dim ls_temp_tim As List
			ls_temp_tim.Initialize
			ls_temp_tim=File.ReadList(File.DirInternal,"save_temp")
			lbl_vorod_time.Text=ls_temp_tim.Get(0)
			lbl_khoroj_time.Text=ls_temp_tim.Get(1)
			et_tozih.Text=ls_temp_tim.Get(2)
			show_tim_lbl1
		Catch
			
			Log(LastException)
		End Try
		
		
		
		
	End If
	
End Sub

Sub date_generat
	date_str=sp_date_year.SelectedItem&"/"&convert_add(sp_date_moon.SelectedIndex+1)&"/"&sp_date_day.SelectedItem
End Sub

Sub convert_add (adad As Int) As String
	
	Dim res As String=adad
	Select adad
		Case 0
			res="00"
		Case 1
			res="01"
		Case 2
			res="02"
		Case 3
			res="03"
		Case 4
			res="04"
		Case 5
			res="05"
		Case 6
			res="06"
		Case 7
			res="07"
		Case 8
			res="08"
		Case 9
			res="09"
	End Select
	

	Return res
End Sub

Sub fill_list
	
	Dim sal As String=et_sall.Text
	Dim mah As String="03"
	
	str_html.Initialize
	
	mah=convert_add(sp_mah.SelectedIndex+1)
	
	FileName="TaradodHa-"&sal&"-"&mah&".html"
		
	str_html.Append("<html dir='rtl'><meta charset='UTF-8' />  <meta name='viewport' content='width=device-width, initial-scale=1.0' />")
	
	str_html.Append("<head><style>table, th, td {border: 1px solid black;border-collapse: collapse;}</style></head><body>")
	
	
	str_html.Append("<h4 align='center' >گزارش تردد ها</h4>")
	str_html.Append("<h6 align='center' >"&sal&"/"&mah&"</h6>")
	
	
	str_html.Append("<table style='width:100%'><tr>")
	str_html.Append("<th>ردیف</th>")
	str_html.Append("<th>تاریخ</th>")
	str_html.Append("<th>ورود</th>")
	str_html.Append("<th>خروج</th>")
	str_html.Append("<th>توضیحات</th>")
	str_html.Append("</tr>")
	
	
	
	
	
	
	
	Dim list1 As List
	list1.Initialize
	list1=myFunc.get_data(sal,mah)
	
	
	
	cusListV_data.Clear
	
	Dim all_tim_h As Int=0
	Dim all_tim_m As Int=0
	
	
	
	For i=0 To list1.Size-1
		
		p = xui.CreatePanel("p")
		p.SetLayoutAnimated(0, 0, 0, 95%x, 110dip)
		p.LoadLayout("irem_data")
		Dim list2() As String=list1.Get(i)
		
		cusListV_data.Add(p,list2(0))
		lbl_edit_item.Tag=list2(0)
		lbl_del_item.Tag=list2(0)
		
		lbl_date_item.Text=list2(1)
		lbl_vorod_item.Text=list2(2)
		lbl_khoroj_item.Text=list2(3)
		lbl_tozih_item.Text=list2(6)
		
		lbl_day_name.Text=myFunc.get_day_name(list2(1))
		
		all_tim_h=all_tim_h+list2(4)
		all_tim_m=all_tim_m+list2(5)
		
		
		str_html.Append("<tr>")
		str_html.Append("<td>"&(i+1)&"</td>")
		str_html.Append("<td>"&list2(1)&"</td>")
		str_html.Append("<td>"&list2(2)&"</td>")
		str_html.Append("<td>"&list2(3)&"</td>")
		str_html.Append("<td>"&list2(6)&"</td>")
		str_html.Append("</tr>")
	
		
		
		
		
	
		
	Next
	
	
	
	If(all_tim_m>59)Then
		all_tim_h=all_tim_h+(all_tim_m / 60)
		all_tim_m=all_tim_m Mod 60
	End If
	
	lbl_show_allTime.Text=all_tim_h &" ساعت و "&all_tim_m &" دقیقه "
	
	
	
	str_html.Append("</table>")
	
	
	str_html.Append("<h4>مجموع ساعات : "&lbl_show_allTime.Text&"</h4>")
	str_html.Append("</body></html>")
	
End Sub
Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Private Sub lbl_save_Click
	Try
		date_generat
		Dim res_H_M As List =myFunc.time_show2(date_str,lbl_vorod_time.Text,lbl_khoroj_time.Text)
	
		
		myFunc.add_taradod(date_str,lbl_vorod_time.Text,lbl_khoroj_time.Text,res_H_M.Get(0),res_H_M.Get(1),et_tozih.Text,0)
		fill_list
		et_tozih.Text=""
		ToastMessageShow("تردد ذخیره شد",False)
	
	Catch
		ToastMessageShow("خطا در ثبت",False)
		Log(LastException)
	End Try
	
End Sub

Sub show_tim_lbl1
	Dim res_H_M As List =myFunc.time_show2(date_str,lbl_vorod_time.Text,lbl_khoroj_time.Text)
	lbl_show_time.Text=" ساعت "&res_H_M.Get(0)&" و "&res_H_M.Get(1)&" دقیقه "
End Sub


Sub show_tim_lbl2
Dim res_H_M As List =myFunc.time_show2(et_date_edit.Text,lbl_vorod_time_edit.Text,lbl_khoroj_time_edit.Text)
	lbl_show_time_edit.Text="ساعت "&res_H_M.Get(0)&" و "&res_H_M.Get(1)&"دقیقه "
End Sub


Private Sub lbl_khoroj_tap_Click
	Dim tim_str As String=""
	tim_str=DateTime.Time(DateTime.Now).SubString2(0,5)
	lbl_khoroj_time.Text=tim_str
	
	show_tim_lbl1
	save_temp
End Sub

Private Sub lbl_vorod_tap_Click
	Dim tim_str1 As String=""
	tim_str1=DateTime.Time(DateTime.Now).SubString2(0,5)
	lbl_vorod_time.Text=tim_str1
	
	
	show_tim_lbl1
	save_temp
	
End Sub



Private Sub lbl_edit_item_Click
	Dim lb As Label=Sender
	
	current_id=lb.Tag
	
	
	
	
	
	Dim list_rec As List=myFunc.get_by_id(current_id)
	
	
	
	'list_rec.Get(0)
	et_date_edit.Text=list_rec.Get(1)
	lbl_vorod_time_edit.Text=list_rec.Get(2)
	lbl_khoroj_time_edit.Text=list_rec.Get(3)
	
	
	'list_rec.Get(4)
	'list_rec.Get(5)
	
	et_tozih_edit.Text=list_rec.Get(6)
	
	show_tim_lbl2

pan_all_edit.Visible=True
	
End Sub



Private Sub pan_all_edit_Click
	pan_all_edit.Visible=False
End Sub

Private Sub lbl_save_edit_Click
	Try
		Dim res_H_M As List =myFunc.time_show2	(et_date_edit.Text,lbl_vorod_time_edit.Text,lbl_khoroj_time_edit.Text)
		
		
		myFunc.edit_taradod(current_id,et_date_edit.Text,lbl_vorod_time_edit.Text,lbl_khoroj_time_edit.Text,res_H_M.Get(0),res_H_M.Get(1),et_tozih_edit.Text,1)
		
		pan_all_edit.Visible=False
		fill_list
		ToastMessageShow("تردد ویرایش شد",False)
		
	Catch
		ToastMessageShow("خطا در ویرایش ",False)
		Log(LastException)
	End Try
	
End Sub

 

Private Sub lbl_vorod_time_edit_Click
	
	Dim tt() As String= Regex.Split(":",lbl_vorod_time_edit.Text)
	Dim tt_h As Int=tt(0)
	Dim tt_m As Int=tt(1)
	
	dialog_tim.SetTime(tt_h,tt_m,True)
	
	Dim j As Int= dialog_tim.Show("","زمان ورود","باشه","","نه",Null)
	If j=DialogResponse.POSITIVE Then
		lbl_vorod_time_edit.Text=dialog_tim.Hour&":"&dialog_tim.Minute
		
		show_tim_lbl2
	End If
	
End Sub

Private Sub lbl_khoroj_time_edit_Click
	
	Dim tt() As String= Regex.Split(":",lbl_khoroj_time_edit.Text)
	Dim tt_h As Int=tt(0)
	Dim tt_m As Int=tt(1)
	
	dialog_tim.SetTime(tt_h,tt_m,True)
	
	Dim j As Int= dialog_tim.Show("","زمان ورود","باشه","","نه",Null)
	If j=DialogResponse.POSITIVE Then
		lbl_khoroj_time_edit.Text=dialog_tim.Hour&":"&dialog_tim.Minute
		
		show_tim_lbl2
	End If
End Sub

Private Sub lbl_vorod_time_Click
	
	
	Dim tt() As String= Regex.Split(":",lbl_vorod_time.Text)
	Dim tt_h As Int=tt(0)
	Dim tt_m As Int=tt(1)
	
	dialog_tim.SetTime(tt_h,tt_m,True)
	
	Dim j As Int= dialog_tim.Show("","زمان ورود","باشه","","نه",Null)
	If j=DialogResponse.POSITIVE Then
		lbl_vorod_time.Text=dialog_tim.Hour&":"&dialog_tim.Minute
		
		show_tim_lbl1
	End If
	save_temp
	
End Sub

Sub save_temp
	
	Dim ls_save As List
	ls_save.Initialize
	
	ls_save.Add(lbl_vorod_time.Text)
	ls_save.Add(lbl_khoroj_time.Text)
	ls_save.Add(et_tozih.Text)
	
	File.WriteList(File.DirInternal,"save_temp",ls_save)
	
End Sub

Private Sub lbl_khoroj_time_Click
	
	Dim tt() As String= Regex.Split(":",lbl_khoroj_time.Text)
	Dim tt_h As Int=tt(0)
	Dim tt_m As Int=tt(1)
	
	dialog_tim.SetTime(tt_h,tt_m,True)
	
	Dim j As Int= dialog_tim.Show("","زمان خروج","باشه","","نه",Null)
	If j=DialogResponse.POSITIVE Then
		lbl_khoroj_time.Text=dialog_tim.Hour&":"&dialog_tim.Minute
		
		show_tim_lbl1
	End If
	save_temp
	
End Sub

Private Sub Panel6_Click
	
End Sub

Private Sub lbl_show_list_Click
	fill_list
End Sub

Private Sub lbl_del_item_Click
	Dim lb As Label=Sender
	
	current_id=lb.Tag
	
	Dim result As Int
	result = Msgbox2("آیا این مورد حذف شود؟", "حذف", "بله", "", "خیر", Null)
	If result = DialogResponse.Positive Then
		myFunc.delete_taradod(current_id)
		pan_all_edit.Visible=False
		fill_list
	End If
	
End Sub

Private Sub lbl_cancel_edit_Click
	pan_all_edit.Visible=False
End Sub


Sub Activity_KeyPress (KeyCode As Int) As Boolean
	If KeyCode = KeyCodes.KEYCODE_BACK Then   
		If(pan_all_edit.Visible=True)Then
			pan_all_edit.Visible=False
			Else
				ExitApplication                        ' Hardware-Zurück Taste gedrückt
		End If
		Return True                                             ' Hardware-Zurück Taste deaktiviert
	Else
		Return False
	End If
End Sub


Private Sub lbl_share_Click
	
	File.WriteString(Starter.Provider.SharedFolder,FileName,str_html)
	
	Dim email As Email
	email.To.Add("aaa@bbb.com")
	email.Subject = "subject"
	email.Body = ""
	email.Attachments.Add(Starter.Provider.GetFileUri(FileName))
	
	Dim in As Intent = email.GetIntent
	in.Flags = 1 'FLAG_GRANT_READ_URI_PERMISSION
	StartActivity(in)
End Sub



Private Sub et_tozih_TextChanged (Old As String, New As String)
	save_temp
End Sub

Private Sub sp_mah_ItemClick (Position As Int, Value As Object)
	lbl_show_list_Click
End Sub