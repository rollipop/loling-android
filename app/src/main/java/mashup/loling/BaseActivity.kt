package mashup.loling


import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import mashup.loling.user.viewmodel.DisposableViewModel

abstract class BaseActivity<T: ViewDataBinding>:  AppCompatActivity(){
    lateinit var viewDataBinding: T
    abstract val layoutResourceId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, layoutResourceId)
    }

}