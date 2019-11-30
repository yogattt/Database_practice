package hospital;

public class info {
	public  String[] tablename= {"MEDICALWORKER","PATIENT","MEDICALRECORDS","OFFICE",
			"PATIENT_WORKER","PSITE","HOSPITALRECORDS","ROOM"};
	public String [][]columnName= {
			{"MNO","MNAME","ONO","AGE","SEX","PROTITLE","MSTATE"},
			{"PANO","PNAME","AGE","SEX","ADDRESS","PHONE"},
			{"PANO","TREATEDATE","MRECORDS","MNO"},{"ONO","ONAME","DNO","NNO","PHONE"},
			{"PANO","DNO","NNO"},{"PANO","ONO","RNO","BNO"},
			{"PANO","INDATE","OUTDATE"},{"RNO","ONO","BEDNUM","RESTNUM"}};
	public String[] []workertable = {
			{"工号","姓名","科室号","年龄","性别","职称","状态"},{"编号","姓名","年龄","性别","住址","电话"},
			{"病人编号","治疗时间","治疗记录","负责人工号"},{"科室号","科室名","主任","护士长","电话"},
			{"病人","主治医生","负责护士"},{"病人编号","科室号","病房号","病床号"},
			{"病人编号","住院日期","出院日期"},{"病房号","所属科室","病床总数","空闲病床数"}};
	public String where=" ";
}
