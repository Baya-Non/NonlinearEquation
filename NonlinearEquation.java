package pr2Calc;
import java.lang.Math;

public class NonlinearEquation{
        
    public static final double EPSILON = 0.001;
    public static final int MAXIMUM_IT = 100;
    public static final int num = 41;//出席番号
	public static final double POSITIVE_MAX = 5.0;
    public static final double NEGATIVE_MAX = 0.0;
	public static final double ALPHA = 1.0;
	
    private double initialValue_;
    private double answer_;
    private int iteration_;
    
    // コンストラクタ(最低一つ用意せよ）
    public NonlinearEquation(double number){
            initialValue_ = number;
    }
    
    /*******************線形反復法*******************/
    private int _solveNLEByLinearIteration(){
        double value;      // x_k に対応
        double pastValue;  // x_{k-1} に対応（初回のpastValue = x_0とする）
        int k;
        iteration_ = 0;
        // 初期反復解をvalueに設定し
		value = initialValue_;
		k=num%10;
		if(k==0) k=10;
		// |value - pastValue| が EPSILON 未満となる(近似解が見つかる)、もしくは
		do{
			pastValue = value;
			value=Math.sqrt(10+k+pastValue);
			iteration_++;
			System.out.println("繰り返し回数"+iteration_+"回目 , Value:"+value+" , pastValue"+pastValue);
			if(iteration_ > MAXIMUM_IT){
				System.out.printf("解が見つかりませんでした．");
				System.exit(1);
			}
		}while(Math.abs(pastValue-value) > EPSILON);
		answer_ = value;
		return iteration_;
			
		// 繰り返し回数がMAXIMUM_IT 回に到達するまで繰り返し
		// 繰り返しで得られる反復解の途中経過を表示するようにすること
    }
	
	
	/*******************二文法*******************/
	private boolean _solveNLEByBisectionMethod(){
		double  xMin=0.0,//最小値
		xMid=0.0,//中間値
		xMax=0.0,//最大値
		xPastMid=0.0,//前回の中間値
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
	
	/*******************はさみうち法*******************/
	
	private boolean _solveNLEByRegulaFalsi(){
		double  xMin=0.0,//最小値
		xNext=0.0,//交点
		xMax=0.0,//最大値
		xPastNext=0.0,//前回の交点
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
		double	xAns,//交点の座標
				xAnsDen,//交点の座標の分母
				xAnsMol;//交点の座標の分子
		
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
			System.out.println("解が見つかりませんでした。");
			System.exit(1);
		}
			
			
                // 初期反復解を設定
                // 近似解が得られたら近似解＆何回目の繰り返しで解が得られたかを表示
                // 得られなければ、解が見つからなかったことを表示  
    }
}