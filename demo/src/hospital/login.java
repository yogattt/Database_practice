package hospital;
import javax.swing.*;
import java.awt.*;   //导入必要的包
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class login extends JFrame{
    JTextField jTextField ;
    JPasswordField jPasswordField;
    JLabel jLabel1,jLabel2;
    JButton jb1;
    private Connection con;
    Statement statement = null;
    ResultSet resultSet = null;
	public login() {
		// TODO Auto-generated constructor stub
		this.setbackground(this);
		this.setSize(400, 300);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb", "ttt","ttt");
			statement = con.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        jTextField = new JTextField(13);
        jTextField.setBounds(120,30,180,35);
        jTextField.setOpaque(false);
        jPasswordField = new JPasswordField(13);
        jPasswordField.setBounds(120,80,180,35);
        jPasswordField.setOpaque(false);
        jLabel1 = new JLabel("账号");
        jLabel1.setBounds(90, 30, 40, 35);
        jLabel2 = new JLabel("密码");
        jLabel2.setBounds(90, 80, 40, 35);
        jb1 = new JButton("确认");
        jb1.setOpaque(false);
        jb1.setBounds(170, 140, 60, 30);
        jb1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event) {
    	    	String user=jTextField.getText();
    	    	String pass=jPasswordField.getText();
    	    	if(user.length()==0||pass.length()==0)
    	    		return;
    	    	try {
					resultSet = statement.executeQuery("select  * from login where ID="+user+" and password="+pass);
					if(resultSet.next()) {
						setVisible(false);// 本窗口隐藏,
		                new mainWindow(con).setVisible(true);// 新窗口显示
		                dispose();//本窗口销毁,释放内存资源
					}else jPasswordField.setText("");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	     }
            }
    	    );
        this.add(jb1);
        this.add(jLabel1); 
        this.add(jTextField);
        this.add(jPasswordField);
        this.add(jLabel2);
        jLabel2.setBounds(90, 80, 40, 35);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("登陆");
        this.setVisible(true);
	}
    public void setbackground(JFrame jf)
    {
        ImageIcon image=new ImageIcon("./pic/bg.png");
        JLabel jlb=new JLabel(image);
        jlb.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
        jf.getLayeredPane().add(jlb,new Integer(Integer.MIN_VALUE));
        JPanel contentPanel=(JPanel)jf.getContentPane();
        contentPanel.setOpaque(false);
    }
	public static void main(String[] args){
		new login();
    }
}