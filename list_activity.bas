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


	Dim current_id As Int=0
	
	Dim dialog_tim As TimeDialog
	Private lbl_show_allTime As Label
	Private cusListV_data As CustomListView
	Private et_mah As EditText
	Private et_sall As EditText
	Private xui As XUI
	Dim p As B4XView
	
	Private lbl_date_item As Label
	Private lbl_vorod_item As Label
	Private lbl_khoroj_item As Label
	Private lbl_tozih_item As Label
	Private lbl_edit_item As Label
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("list_layout")

	fill_list("1401","03")
	dialog_tim.Is24Hours=True
End Sub

Sub fill_list(sal As String , mah As String)
	Dim list1 As List
	list1.Initialize
	list1=myFunc.get_data(sal,mah)
	
	
	
	
	
	
	cusListV_data.Clear
	
	Dim all_tim_h As Int=0
	Dim all_tim_m As Int=0
	
	
	
	For i=0 To list1.Size-1
		p = xui.CreatePanel("p")
		p.SetLayoutAnimated(0, 0, 0, 95%x, 120dip)
		p.LoadLayout("irem_data")
		Dim list2() As String=list1.Get(i)
		
		cusListV_data.Add(p,list2(0))
		lbl_edit_item.Tag=list2(0)
		
		lbl_date_item.Text=list2(1)
		lbl_vorod_item.Text=list2(2)
		lbl_khoroj_item.Text=list2(3)
		lbl_tozih_item.Text=list2(6)
		
		all_tim_h=all_tim_h+list2(4)
		all_tim_m=all_tim_m+list2(5)
		
	Next
	
	If(all_tim_m>59)Then
		all_tim_h=all_tim_h+(all_tim_m / 60)
		all_tim_m=all_tim_m Mod 60
	End If
	
	lbl_show_allTime.Text=all_tim_h &"ساعت و "&all_tim_m &"دقیقه "
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Private Sub lbl_back_Click
	StartActivity(home_activity)
	Activity.Finish
End Sub

Private Sub lbl_show_list_Click
	fill_list(et_sall.Text,et_mah.Text)
End Sub