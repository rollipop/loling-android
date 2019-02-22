package mashup.loling.drawpaper.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_draw_paper.*
import mashup.loling.R
import mashup.loling.drawpaper.Component
import mashup.loling.room.view.ReceivedPaperListActivity
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class DrawPaperActivity : AppCompatActivity(), IComponentTouchListener {
    private val REQUEST_CODE_GET_IMAGE = 100

    private val CLICK_TIME_GAP = 200
    private val CLICK_DIST_THRESHOLD = 10
    private val LONG_CLICK_TIME = 500L

    private val componentList: ArrayList<Component> = ArrayList()
    private var selectedComponent: Component? = null

    private var selectedName = ""
    private var selectedPhoneNum = ""
    private var selectedDate = 0L

    enum class State { LOLING_EDIT, TEXT_EDIT, IV_EDIT }
    private var currentState = State.LOLING_EDIT

    private val colorList = arrayOf(
        R.color.draw_paper_txt_black,
        R.color.draw_paper_txt_red,
        R.color.draw_paper_txt_yellow,
        R.color.draw_paper_txt_green,
        R.color.draw_paper_txt_blue,
        R.color.draw_paper_txt_violet,
        R.color.draw_paper_txt_white
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_paper)

        // Add image or text
        btnDrawPaperCamera.setOnClickListener { btnDrawPaperCameraClicked() }
        btnDrawPaperText.setOnClickListener { btnDrawPaperTextClicked() }

        // Text size change
        btnTextSizeUp.setOnClickListener { btnTextSizeChangeClicked(sizeUp = true) }
        btnTextSizeDown.setOnClickListener { btnTextSizeChangeClicked(sizeUp = false) }

        // Component zIndex change
        btnTextZindexUp.setOnClickListener { btnZindexAdjustClicked(isUp = true) }
        btnTextZindexDown.setOnClickListener { btnZindexAdjustClicked(isUp = false) }
        btnIvZindexUp.setOnClickListener { btnZindexAdjustClicked(isUp = true) }
        btnIvZindexDown.setOnClickListener { btnZindexAdjustClicked(isUp = false) }

        // Close component edit
        btnCloseEditTextPanel.setOnClickListener { closeEditPanel() }
        btnCloseIvPanel.setOnClickListener { closeEditPanel() }

        // Exit DrawPaper
        btnDrawPaperClose.setOnClickListener { exitDrawPaper() }

        // Save DrawPaper
        btnDrawPaperSave.setOnClickListener { saveDrawPaper() }

        generateTvColorChangeBtn()

        // receive passed parameters
        selectedName = intent.getStringExtra("name")
        selectedPhoneNum = intent.getStringExtra("phoneNum")
        selectedDate = intent.getLongExtra("date", 0)
    }

    private fun generateTvColorChangeBtn() {
        for (colorId: Int in colorList) {
            val colorInt = Color.parseColor(getString(colorId))
            val btnSize = resources.getDimensionPixelSize(R.dimen.draw_paper_panel_color_btn_size)
            val newIvChangeTxtColor = ImageView(this)
            val newIvChangeBgColor = ImageView(this)

            newIvChangeTxtColor.scaleType = ImageView.ScaleType.CENTER
            newIvChangeBgColor.scaleType = ImageView.ScaleType.CENTER

            val shapeTxt = GradientDrawable()
            shapeTxt.shape = GradientDrawable.OVAL
            shapeTxt.setColor(colorInt)
            shapeTxt.setStroke(2, Color.WHITE)
            shapeTxt.setSize(btnSize, btnSize)
            newIvChangeTxtColor.setImageDrawable(shapeTxt)

            val shapeBg = GradientDrawable()
            shapeBg.shape = GradientDrawable.RECTANGLE
            shapeBg.setColor(colorInt)
            shapeBg.setStroke(2, Color.WHITE)
            shapeBg.setSize(btnSize, btnSize)
            newIvChangeBgColor.setImageDrawable(shapeBg)

            pnTvColorSelector.addView(newIvChangeTxtColor)
            pnTvBgSelector.addView(newIvChangeBgColor)

            val paramTxt = newIvChangeTxtColor.layoutParams as LinearLayout.LayoutParams
            val paramBg = newIvChangeBgColor.layoutParams as LinearLayout.LayoutParams

            paramTxt.width = btnSize
            paramTxt.weight = 1f
            paramTxt.height = btnSize
            newIvChangeTxtColor.layoutParams = paramTxt

            paramBg.width = btnSize
            paramBg.weight = 1f
            paramBg.height = btnSize
            newIvChangeBgColor.layoutParams = paramBg

            // listener
            newIvChangeTxtColor.setOnClickListener {
                selectedComponent?.let {
                    if(it.view is TextView)
                        it.view.setTextColor(colorInt)
                }
            }
            newIvChangeBgColor.setOnClickListener {
                selectedComponent?.let {
                    it.view.setBackgroundColor(colorInt)
                }
            }

        }
    }

    private fun btnTextSizeChangeClicked(sizeUp: Boolean) {
        selectedComponent?.let {
            if(it.view !is TextView) return@let
            when (sizeUp) {
                true -> {
                    it.increaseTextSizeRequest()
                }
                false -> {
                    it.decreaseTextSizeRequest()
                }
            }
        }
    }

    private fun btnZindexAdjustClicked(isUp: Boolean) {
        selectedComponent?.let {
            when (isUp) {
                true -> {
                    // 자기보다 바로 에 있위는 애의 zIndex를 가져온다
                    var aboveMin = Int.MAX_VALUE
                    for(comp: Component in componentList) {
                        if(comp != it && comp.zIndex > it.zIndex && comp.zIndex < aboveMin)
                            aboveMin = comp.zIndex
                    }
                    // aboveMin 갱신이 없으면 이미 얘가 왕임
                    if(aboveMin == Int.MAX_VALUE) return
                    // 걔보다 1 더 높게
                    val targetZIndex = aboveMin + 1
                    // zindex가 겹치면 안되기 때문에, 리스트 중 targetIndex와 같거나 높은 모든 컴포넌트의 zIndex를 +1 해준다
                    for(comp: Component in componentList) {
                        if(!comp.equals(it) && comp.zIndex >= targetZIndex)
                            comp.zIndex += 1
                    }
                    it.zIndex = targetZIndex
                }
                false -> {
                    // 자기보다 바로 아래에 있는 애의 zIndex를 가져온다
                    var underMax = -1
                    for(comp: Component in componentList) {
                        if(comp != it && comp.zIndex < it.zIndex && comp.zIndex > underMax)
                            underMax = comp.zIndex
                    }
                    // underMax 갱신이 없으면 이미 얘가 바닥임
                    if(underMax == -1) return
                    val targetZIndex = underMax
                    // zindex가 겹치면 안되기 때문에, 리스트 중 targetIndex와 같거나 높은 모든 컴포넌트의 zIndex를 +1 해준다
                    for(comp: Component in componentList) {
                        if(!comp.equals(it) && comp.zIndex >= targetZIndex)
                            comp.zIndex += 1
                    }
                    it.zIndex = targetZIndex
                }
            }

            orderViews()
        }
    }

    private fun btnDrawPaperCameraClicked() {
        val imgIntent = Intent(Intent.ACTION_PICK)
        imgIntent.type = "image/*"
        startActivityForResult(imgIntent, REQUEST_CODE_GET_IMAGE)
    }

    private fun btnDrawPaperTextClicked() {
        val newComponent = Component(TextView(this), getMostTopZIndex()+1, this)
        // test data
        (newComponent.view as TextView).text = "Hello"
        newComponent.view.setTextColor(Color.BLACK)
        newComponent.view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
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

                    val newComponent = Component(ImageView(this), getMostTopZIndex()+1, this)
                    (newComponent.view as ImageView).setImageBitmap(resizedBmp)
                    newComponent.view.scaleType = ImageView.ScaleType.FIT_XY
                    addComponent(newComponent)

                    Log.v(DrawPaperActivity::class.java.simpleName, "New Image Component Added!!")
                }
            }
        }
    }

    override fun onBackPressed() {
        // 컴포넌트 편집상태라면 편집 전으로, 롤링 편집상태라면 만들기 취소하고 버리시겠습니까? 메시지박스
        when (currentState) {
            State.LOLING_EDIT -> {
                exitDrawPaper()
            }

            State.TEXT_EDIT, State.IV_EDIT -> {
                closeEditPanel()
            }
        }
    }

    private fun exitDrawPaper() {
        val dialog = AlertDialog.Builder(this, R.style.AlertDialogStyle)
        dialog.setMessage(R.string.draw_activity_msg_do_you_really_wanna_go_back_and_discard)
                .setCancelable(false)
                .setPositiveButton(R.string.btn_yes) { _, _ ->
                    super.onBackPressed()
                    return@setPositiveButton
                }.setNegativeButton(R.string.btn_cancel) { _, _ ->
                    return@setNegativeButton
                }.create().show()
    }

    private fun saveDrawPaper() {
        // 물어본다
        val dialog = AlertDialog.Builder(this, R.style.AlertDialogStyle)
        dialog.setMessage(R.string.draw_activity_msg_do_you_want_to_submit_your_paper)
                .setCancelable(false)
                .setPositiveButton(R.string.btn_yes) { _, _ ->
                    // 크기가져와
                    val displayMetrics = DisplayMetrics()
                    windowManager.defaultDisplay.getMetrics(displayMetrics)
                    val w = displayMetrics.widthPixels
                    val h = displayMetrics.heightPixels

                    // View를 Bitmap으로 변환
                    drawArea.layout(0, 0, w,h)
                    val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
                    bmp.eraseColor(Color.TRANSPARENT)
                    val canvas = Canvas(bmp)
                    drawArea.draw(canvas)

                    // 저장
                    val fileName = "loling/loling_" + SimpleDateFormat("yyMMdd-hhmmss", Locale.getDefault()).format(Date()) + ".png"
                    val path = Environment.getExternalStorageDirectory()
                    val dir = File(path, "loling")
                    if(!dir.exists()) dir.mkdir()
                    val file = File(path, fileName)
                    file.createNewFile()

                    val fileOutputStream = FileOutputStream(file, false)
                    try {
                        bmp.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.v("JUJIN", "Cannot save img")
                    }

                    // 다음 액티비티로 가즈아
                    val intent = Intent(this, ReceivedPaperListActivity::class.java)
                    intent.putExtra("name", selectedName)
                    intent.putExtra("phoneNum", selectedPhoneNum)
                    intent.putExtra("date", selectedDate)
                    startActivity(intent)

                    return@setPositiveButton
                }.setNegativeButton(R.string.btn_cancel) { _, _ ->
                    return@setNegativeButton
                }.create().show()
    }

    /** Text 컴포넌트에서 문자열 수정시 즉시 적용될 수 있도록 하는 watcher */
    private val textChangeWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            selectedComponent?.let {
                if(it.view is TextView && s != null) {
                    it.view.text = s.toString()
                }
            }
        }
    }

    private fun closeEditPanel() {
        if (selectedComponent != null) {
            selectedComponent?.onComponentUnselected()
            selectedComponent = null
        }
        pnTvEdit.visibility = View.GONE
        pnIvEdit.visibility = View.GONE
        pnAddComponent.visibility = View.VISIBLE
        txtTvEdit.removeTextChangedListener(textChangeWatcher)

        currentState = State.LOLING_EDIT
    }

    override fun onComponentTouchedDown(component: Component) {
        if(selectedComponent == null) {
            // 이미 선택된 컴포넌트가 있다면 해당 컴포넌트 컨트롤을 유지한다
            selectedComponent = component
            selectedComponent?.onComponentSelected()
        }
    }

    override fun onComponentClicked(component: Component) {
        // 컴포넌트가 텍스트뷰면 텍스트 수정모드로 들어간다
        if (component.view is TextView) {
            pnAddComponent.visibility = View.GONE
            pnTvEdit.visibility = View.VISIBLE
            txtTvEdit.setText(component.view.text)
            currentState = State.TEXT_EDIT

            txtTvEdit.addTextChangedListener(textChangeWatcher)

        } else  // 컴포넌트가 이미지뷰면 zindex조절만
        if (component.view is ImageView) {
            pnAddComponent.visibility = View.GONE
            pnIvEdit.visibility = View.VISIBLE
            currentState = State.IV_EDIT
        }
    }

    override fun onComponentLongClicked(component: Component) {
        val dialog = AlertDialog.Builder(this, R.style.AlertDialogStyle)
        dialog.setMessage(R.string.draw_activity_msg_do_you_want_to_delete_selected_component)
                .setCancelable(false)
                .setPositiveButton(R.string.btn_yes) { _, _ ->
                    removeComponent(component)
                    selectedComponent = null
                    return@setPositiveButton
                }.setNegativeButton(R.string.btn_cancel) { _, _ ->
                    return@setNegativeButton
                }.create().show()
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

    /** 꾸미기 컴포넌트를 삭제한다 */
    private fun removeComponent(component : Component) {
        componentList.remove(component)
        drawArea.removeView(component.view)
    }

    // variables for position
    private var originTouchPoint = PointF(0f, 0f)
    private var originLeft = 0f
    private var originTop = 0f
    // variables for scale
    private var originDistance = 0f
    private var originScale = 0f
    // variables for rotation
    private var originDegree = 0f
    private var originRotation = 0f
    // variables to implement click/long click event
    private var touchedTime: Long = 0   // timestamp
    private var isStillHolding = false   // for long click
    private val longClickHandler = Handler()
    private val longClickRunnable = Runnable {
        selectedComponent?.let { if(isStillHolding) onComponentLongClicked(it); isStillHolding = false }
    }
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
                    originLeft = it.view.translationX
                    originTop = it.view.translationY

                }

                // 터치된 시간 저장
                touchedTime = System.currentTimeMillis()

                // 롱 터치 인식을 위한 flag on
                isStillHolding = true
                longClickHandler.removeCallbacks(longClickRunnable)
                longClickHandler.postDelayed(longClickRunnable, LONG_CLICK_TIME)

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
                isStillHolding = false
            }

            MotionEvent.ACTION_MOVE -> {    // 무-브
                // 처음 터치 위치로부터 얼마나 움직였나?
                val dMove = PointF(ev.getX(0), ev.getY(0))
                dMove.offset(-originTouchPoint.x, -originTouchPoint.y)

                // 처음 손가락 거리로부터 얼마나 벌어졌나? (기준 1, 몇 배나 벌어졌는지) (1개 손가락일 경우 0)
                var dDist = if(pointerCnt < 2) 0f else calculateDistance(ev) / originDistance

                // 처음 두 손가락 좌표의 기울기로부터 얼마나 돌아갔나? (degree로) (1개 손가락일 경우 0)
                var dDeg = if(pointerCnt < 2) 0f else calculateDegree(ev) - originDegree

                selectedComponent?.let {
                    // 뷰 이동
                    val lParam: RelativeLayout.LayoutParams = it.view.layoutParams as RelativeLayout.LayoutParams
                    it.view.translationX = originLeft + dMove.x
                    it.view.translationY = originTop + dMove.y
                    it.view.layoutParams = lParam

                    // 뷰 확대축소/회전(2개 손가락일 경우에만)
                    if(pointerCnt > 1) {
                        if (dDist.isNaN() || dDist == 0f) dDist = 1f
                        it.scale(originScale * dDist)
                        it.rotate(originRotation + dDeg)
                    }
                }

                if(Math.abs(dMove.x) > CLICK_DIST_THRESHOLD || Math.abs(dMove.y) > CLICK_DIST_THRESHOLD || pointerCnt != 1) {
                    isStillHolding = false
                }
            }

            MotionEvent.ACTION_POINTER_UP -> {  // 터치 업
                selectedComponent?.let {
                    // 두 손가락 중 첫번째 댓던 손가락을 때면 선택된 컴포넌트 없음으로 한다
                    if (ev.actionIndex == 0) {
                        if (isClickGesture(
                                    touchedTime,
                                    System.currentTimeMillis(),
                                    originTouchPoint,
                                    PointF(ev.getX(0), ev.getY(0)))) {
                            onComponentClicked(it)
                        } else if (currentState == State.LOLING_EDIT) {
                            it.onComponentUnselected()
                            selectedComponent = null
                        }
                    }
                }
                isStillHolding = false
            }
            MotionEvent.ACTION_UP -> {  // 마지막 터치 업
                selectedComponent?.let {
                    if (isClickGesture(
                                    touchedTime,
                                    System.currentTimeMillis(),
                                    originTouchPoint,
                                    PointF(ev.getX(0), ev.getY(0)))) {
                        onComponentClicked(it)
                    } else if (currentState == State.LOLING_EDIT) {
                        // 모든 손가락을 때면 선택된 컴포넌트 없음으로 한다 (선택중이 아니면)
                        it.onComponentUnselected()
                        selectedComponent = null
                    }
                }
                isStillHolding = false
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

    private fun isClickGesture(touchDownTime: Long, touchUpTime: Long, touchDownPoint: PointF, touchUpPoint: PointF ) : Boolean {
        if (touchUpTime - touchDownTime > CLICK_TIME_GAP) return false
        if (Math.abs(touchDownPoint.x - touchUpPoint.x) > CLICK_DIST_THRESHOLD ||
            Math.abs(touchDownPoint.y - touchUpPoint.y) > CLICK_DIST_THRESHOLD)
            return false
        return true
    }

    /**
     * 현재 컴포넌트들중 가장 높은 zIndex값을 가져온다
     */
    private fun getMostTopZIndex(): Int{
        var top = 0
        for(comp: Component in componentList) {
            if(comp.zIndex > top) top = comp.zIndex
        }
        return top
    }

    /**
     * 뷰들 보이는 순서를 다시 정리한다.
     * 각 꾸미기 컴포넌트들의 zIndex값이 낮을수록 밑에 보여지고(가려지고), 높을수록 위에 보여진다.
     */
    private fun orderViews() {
        val sortedList = componentList.sortedWith(compareBy{it.zIndex})
        for(comp: Component in sortedList)
            comp.view.bringToFront()
    }

}
