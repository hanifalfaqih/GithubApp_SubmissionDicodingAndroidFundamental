package id.allana.githubapp_bfaa.ui.detail.viewpager.listfollowing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.allana.githubapp_bfaa.data.model.ItemsItem
import id.allana.githubapp_bfaa.databinding.ListItemUserBinding
import id.allana.githubapp_bfaa.ui.detail.DetailUserFragmentDirections
import id.allana.githubapp_bfaa.ui.list.ListUserFragmentDirections

class ListFollowingAdapter: RecyclerView.Adapter<ListFollowingAdapter.ListFollowingViewHolder>() {

    private var listItem: MutableList<ItemsItem> = mutableListOf()

    fun setListItems(listItem: List<ItemsItem>) {
        clearListItems()
        addListItems(listItem)
    }

    private fun clearListItems() {
        this.listItem.clear()
    }

    private fun addListItems(listItem: List<ItemsItem>) {
        this.listItem.addAll(listItem)
    }


    class ListFollowingViewHolder(private val binding: ListItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: ItemsItem) {
            with(item) {
                binding.tvName.text = this.username
                binding.tvUrlProfile.text = this.urlProfile
                Glide.with(itemView)
                    .load(this.avatarUrl)
                    .into(binding.imgAvatar)
                itemView.setOnClickListener {
                    val actionDetail = DetailUserFragmentDirections.actionDetailUserFragmentToDetailUserFragmentForViewPager(this.username)
                    it.findNavController().navigate(actionDetail)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFollowingViewHolder {
        val binding = ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListFollowingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ListFollowingViewHolder, position: Int) {
        holder.bindView(listItem[position])
    }
}