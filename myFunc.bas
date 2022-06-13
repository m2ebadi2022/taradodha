B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=StaticCode
Version=11.5
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	
End Sub


Sub connection_sql
	If(File.Exists(File.DirInternal,"data.db")=False)Then
		File.Copy(File.DirAssets,"data.db",File.DirInternal,"data.db")
	End If
	If (Main.sql.IsInitialized=False)Then
		Main.sql.Initialize(File.DirInternal,"data.db",True)
	End If
End Sub



Sub get_data(sal As String, mah As String) As List
	connection_sql
	Main.res=Main.sql.ExecQuery("SELECT * FROM taradodha WHERE date like '%"&sal&"/"&mah&"%' ORDER BY  id DESC;")
	
	Dim list_result As List
	list_result.Initialize
	
	Do While Main.res.NextRow
		list_result.Add(Array As String(Main.res.GetInt("id"),Main.res.GetString("date"),Main.res.GetString("vorod"),Main.res.GetString("khoroj"),Main.res.GetInt("tim_h"),Main.res.GetInt("tim_m"),Main.res.GetString("tozih")))
		
	Loop
	
	Return list_result
End Sub

Sub fa2en(a As String) As String
	Dim fa As String="۰۱۲۳۴۵۶۷۸۹"
	For la=0 To 9
		a=a.Replace(fa.SubString2(la,la+1),la)
	Next
	Return a
End Sub


Sub add_taradod(date As String,vorod As String,khoroj As String,h As Int,m As Int, tozih As String, state As Int) As Boolean
	connection_sql
	Main.sql.ExecNonQuery2("INSERT INTO taradodha (date , vorod, khoroj , tim_h, tim_m, tozih , state) VALUES (?,?,?,?,?,?,?)", Array As Object(date,vorod, khoroj, h,m,tozih,state))
	Main.sql.Close
	Return True
End Sub