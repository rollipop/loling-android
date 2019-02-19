package mashup.loling.drawpaper.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PointF
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_draw_paper.*
import mashup.loling.R
import mashup.loling.drawpaper.Component
import kotlin.math.roundToInt

class DrawPaperActivity : AppCompatActivity(), IComponentTouchListener {
    private val REQUEST_CODE_GET_IMAGE = 100

    override fun onComponentSelected(component: Component) {
        selectedComponent = component
    }

    private val componentList: ArrayList<Component> = ArrayList()
    private var selectedComponent: Component? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_paper)

        btnDrawPaperCamera.setOnClickListener { btnDrawPaperCameraClicked() }
        btnDrawPaperText.setOnClickListener { btnDrawPaperTextClicked() }

    }

    private fun btnDrawPaperCameraClicked() {
        val imgIntent = Intent(Intent.ACTION_PICK)
        imgIntent.type = "image/*"
        startActivityForResult(imgIntent, REQUEST_CODE_GET_IMAGE)
    }

    private fun btnDrawPaperTextClicked() {
        val newComponent = Component(TextView(this), Component.Companion.ComponentType.TEXT, this)
        // test data
        (newComponent.view as TextView).text = "Hello"
        (newComponent.view as TextView).textSize = 90f
        (newComponent.view as TextView).setBackgroundColor(Color.BLUE)
        addComponent(newComponent)

        Log.v(DrawPaperActivity::class.java.simpleName, "New Text Component Added!!")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode) {
            REQUEST_CODE_GET_IMAGE -> {
                if(resultCode == Activity.RESULT_OK && data != null && data.data != null) {
                    val imgUri = data.data
                    val inputStream = contentResolver.openInputStream(imgUri!!)

                    val bitmap = BitmapFactory.decodeStream(inputStream)

                    // 이미지 가로세로 다시 계산 (적절한 크기)
                    var bWidth = bitmap.width
                    var bHeight = bitmap.height
                    var toWidth = drawArea.width/2
                    var scale = toWidth / bWidth.toFloat()
                    var toHeight = (bHeight * scale).roundToInt()

                    val resizedBmp = Bitmap.createScaledBitmap(bitmap, toWidth, toHeight,  true)

                    val newComponent = Component(ImageView(this), Component.Companion.ComponentType.IMAGE, this)
                    (newComponent.view as ImageView).setImageBitmap(resizedBmp)
                    newComponent.view.scaleType = ImageView.ScaleType.FIT_XY
                    addComponent(newComponent)

                    Log.v(DrawPaperActivity::class.java.simpleName, "New Image Component Added!!")
                }
            }
        }
    }

    /** 새로운 꾸미기 컴포넌트를 추가한다 */
    private fun addComponent(newComponent: Component) {
        componentList.add(newComponent)
        drawArea.addView(newComponent.view)

        val lParams = newComponent.view.layoutParams as RelativeLayout.LayoutParams
        lParams.width = RelativeLayout.LayoutParams.WRAP_CONTENT
        lParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT
        newComponent.view.layoutParams = lParams
        newComponent.view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        lParams.leftMargin = drawArea.width/2 - newComponent.view.measuredWidth/2
        lParams.topMargin = drawArea.height/2 - newComponent.view.measuredHeight/2
        newComponent.view.layoutParams = lParams
    }

    // variables for position
    var originTouchPoint = PointF(0f, 0f)
    var originLeft = 0
    var originTop = 0
    // variables for scale
    var originDistance = 0f
    var originScale = 0f
    // variables for rotation
    var originDegree = 0f
    var originRotation = 0f
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (ev == null) return super.onTouchEvent(ev)

        var pointerCnt = ev.pointerCount    // 터치된 손가락 개수 저장
        pointerCnt = if(pointerCnt > 2) 2 else pointerCnt   // 최대 멀티터치는 두개까지만 제한

        when(ev.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {    // 1개 터치 다운
                // 처음 터치 위치 저장
                originTouchPoint.x = ev.getX(0)
                originTouchPoint.y = ev.getY(0)
                selectedComponent?.let {
                    // 처음 뷰 위치 저장
                    val lParam: RelativeLayout.LayoutParams = it.view.layoutParams as RelativeLayout.LayoutParams
                    originLeft = lParam.leftMargin
                    originTop = lParam.topMargin
                    
                    // 선택된 뷰 맨 앞으로
                    it.view.bringToFront()
                }

            }
            MotionEvent.ACTION_POINTER_DOWN -> {    // 터치 다운
                if(pointerCnt == 2) {   // 두 손가락만 지원
                    selectedComponent?.let {
                        // 처음 뷰의 크기/회전 저장
                        originScale = it.view.scaleX
                        originRotation = it.view.rotation
                    }

                    // 처음 손가락 거리/손가락 좌표 기울기 저장
                    originDistance = calculateDistance(ev)
                    originDegree = calculateDegree(ev)
                }
            }

            MotionEvent.ACTION_MOVE -> {    // 무-브
                selectedComponent?.let {

                    // 처음 터치 위치로부터 얼마나 움직였나?
                    val dMove = PointF(ev.getX(0), ev.getY(0))
                    dMove.offset(-originTouchPoint.x, -originTouchPoint.y)

                    // 처음 손가락 거리로부터 얼마나 벌어졌나? (기준 1, 몇 배나 벌어졌는지) (1개 손가락일 경우 0)
                    var dDist = if(pointerCnt < 2) 0f else calculateDistance(ev) / originDistance

                    // 처음 두 손가락 좌표의 기울기로부터 얼마나 돌아갔나? (degree로) (1개 손가락일 경우 0)
                    var dDeg = if(pointerCnt < 2) 0f else calculateDegree(ev) - originDegree

                    // 뷰 이동
                    val lParam: RelativeLayout.LayoutParams = it.view.layoutParams as RelativeLayout.LayoutParams
                    lParam.leftMargin = originLeft + dMove.x.toInt()
                    lParam.topMargin = originTop + dMove.y.toInt()
                    it.view.layoutParams = lParam

                    // 뷰 확대축소/회전(2개 손가락일 경우에만)
                    if(pointerCnt > 1) {
                        if (dDist.isNaN() || dDist == 0f) dDist = 1f
                        it.scale(originScale * dDist)
                        it.rotate(originRotation + dDeg)
                    }
                }
            }

            MotionEvent.ACTION_POINTER_UP -> {  // 터치 업
                // 두 손가락 중 첫번째 댓던 손가락을 때면 선택된 컴포넌트 없음으로 한다
                if (ev.actionIndex == 0) {
                    selectedComponent = null
                }
            }
            MotionEvent.ACTION_UP -> {  // 마지막 터치 업
                // 모든 손가락을 때면 선택된 컴포넌트 없음으로 한다
                selectedComponent = null
            }
        }

        return super.onTouchEvent(ev)
    }

    private fun calculateDistance(ev: MotionEvent): Float {
        if(ev.pointerCount < 2) return 0f
        return calculateDistance(
                ev.getX(0), ev.getY(0),
                ev.getX(1), ev.getY(1))
    }
    private fun calculateDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        val dx = (x1 - x2).toDouble()
        val dy = (y1 - y2).toDouble()
        return Math.sqrt((dx*dx)+(dy*dy)).toFloat()
    }

    private fun calculateDegree(ev: MotionEvent): Float {
        if(ev.pointerCount < 2) return 0f
        return calculateDegree(
                ev.getX(0), ev.getY(0),
                ev.getX(1), ev.getY(1))
    }
    private fun calculateDegree(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        val rad = Math.atan2(y1-y2.toDouble(), x1-x2.toDouble())
        return Math.toDegrees(rad).toFloat()
    }

}
