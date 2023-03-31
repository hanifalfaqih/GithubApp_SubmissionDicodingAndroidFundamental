package id.allana.githubapp_bfaa.data.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B: ViewBinding, VM: BaseContract.BaseViewModel>(
    val bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> B
): Fragment(), BaseContract.BaseView {

    private lateinit var binding: B
    private lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingFactory(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = initViewModel()
        initView()
        observeData()
    }

    abstract fun initView()
    abstract fun initViewModel(): VM

    fun getViewBinding(): B = binding
    fun getViewModel(): VM = viewModel

    override fun observeData() {}

    override fun showContent(isVisible: Boolean) {}

    override fun showLoading(isLoading: Boolean) {}

    override fun showError(isError: Boolean, msg: String?) {}
}