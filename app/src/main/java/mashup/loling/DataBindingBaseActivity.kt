package mashup.loling.user.view


import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import mashup.loling.BaseActivity

abstract class DataBindingBaseActivity<T: ViewDataBinding>:  BaseActivity(){
    lateinit var viewDataBinding: T
    abstract val layoutResourceId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, layoutResourceId)
    }

}