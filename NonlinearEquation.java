package pr2Calc;
import java.lang.Math;

public class NonlinearEquation{
        
    public static final double EPSILON = 0.001;
    public static final int MAXIMUM_IT = 100;
    public static final int num = 41;//�o�Ȕԍ�
	public static final double POSITIVE_MAX = 5.0;
    public static final double NEGATIVE_MAX = 0.0;
	public static final double ALPHA = 1.0;
	
    private double initialValue_;
    private double answer_;
    private int iteration_;
    
    // �R���X�g���N�^(�Œ��p�ӂ���j
    public NonlinearEquation(double number){
            initialValue_ = number;
    }
    
    /*******************���`�����@*******************/
    private int _solveNLEByLinearIteration(){
        double value;      // x_k �ɑΉ�
        double pastValue;  // x_{k-1} �ɑΉ��i�����pastValue = x_0�Ƃ���j
        int k;
        iteration_ = 0;
        // ������������value�ɐݒ肵
		value = initialValue_;
		k=num%10;
		if(k==0) k=10;
		// |value - pastValue| �� EPSILON �����ƂȂ�(�ߎ�����������)�A��������
		do{
			pastValue = value;
			value=Math.sqrt(10+k+pastValue);
			iteration_++;
			System.out.println("�J��Ԃ���"+iteration_+"��� , Value:"+value+" , pastValue"+pastValue);
			if(iteration_ > MAXIMUM_IT){
				System.out.printf("����������܂���ł����D");
				System.exit(1);
			}
		}while(Math.abs(pastValue-value) > EPSILON);
		answer_ = value;
		return iteration_;
			
		// �J��Ԃ��񐔂�MAXIMUM_IT ��ɓ��B����܂ŌJ��Ԃ�
		// �J��Ԃ��œ����锽�����̓r���o�߂�\������悤�ɂ��邱��
    }
	
	
	/*******************�񕶖@*******************/
	private boolean _solveNLEByBisectionMethod(){
		double  xMin=0.0,//�ŏ��l
		xMid=0.0,//���Ԓl
		xMax=0.0,//�ő�l
		xPastMid=0.0,//�O��̒��Ԓl
		value;//x_k
		
		
		xMin = NEGATIVE_MAX;
		xMax = POSITIVE_MAX;
		
		
		do{
			iteration_++;
			xPastMid = xMid;
			xMid = (xMin + xMax)/2;
			value = CalcValue(xMid);
			
			System.out.println("xMid = "+ xMid + " , f(xMid) = "+ value + " xPastMid = "+ xPastMid);
			
			if(Math.signum(value) == Math.signum(CalcValue(xMax))){
					xMax = xMid;
			}	else	{
					xMin = xMid;
			}
		}while(Math.abs(xMid-xPastMid)>=EPSILON && value !=0.0);
		
		answer_=xMid;
		return true;
	}
	
	/*******************�͂��݂����@*******************/
	
	private boolean _solveNLEByRegulaFalsi(){
		double  xMin=0.0,//�ŏ��l
		xNext=0.0,//��_
		xMax=0.0,//�ő�l
		xPastNext=0.0,//�O��̌�_
		value;//x_k
		
		
		xMin = NEGATIVE_MAX;
		xMax = POSITIVE_MAX;
		
		
		do{
			iteration_++;
			xPastNext = xNext;
			xNext = LineCross(xMin,xMax);
			value = CalcValue(xNext);
			
			System.out.println("xNext = "+ xNext + " , f(xNext) = "+ value + " xPastMid = "+ xPastNext);
			
			if(Math.signum(value) == Math.signum(CalcValue(xMax))){
					xMax = xNext;
			}	else	{
					xMin = xNext;
			}
		}while(Math.abs(xNext-xPastNext)>=EPSILON && value !=0.0);
		
		answer_=xNext;
		return true;
	}
	
	public double CalcValue(double x){
			
			if(x + ALPHA == 0){
				 return 1.0;
			}	else	{
				return  Math.sin(x + ALPHA)/(x + ALPHA);
			}
	}

	public double LineCross(double xMin,double xMax){
		double	xAns,//��_�̍��W
				xAnsDen,//��_�̍��W�̕���
				xAnsMol;//��_�̍��W�̕��q
		
		xAnsMol = (xMin * CalcValue(xMax))-(xMax * CalcValue(xMin));
		xAnsDen = CalcValue(xMax) - CalcValue(xMin);
		xAns = xAnsMol / xAnsDen;
		
		return xAns;
	}
		
	public static void main(String[] args) {
	        	
		NonlinearEquation eqn = new NonlinearEquation(0);
				
		if(eqn._solveNLEByRegulaFalsi()){
			System.out.println("X = "+eqn.answer_+" at iteration "+ (eqn.iteration_-1));
		}	else	{
			System.out.println("����������܂���ł����B");
			System.exit(1);
		}
			
			
                // ������������ݒ�
                // �ߎ���������ꂽ��ߎ���������ڂ̌J��Ԃ��ŉ�������ꂽ����\��
                // �����Ȃ���΁A����������Ȃ��������Ƃ�\��  
    }
}