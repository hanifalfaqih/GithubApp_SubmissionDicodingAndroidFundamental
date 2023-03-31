package id.allana.githubapp_bfaa.ui.detail.viewpager.listfollower

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.allana.githubapp_bfaa.data.model.ItemsItem
import id.allana.githubapp_bfaa.databinding.ListItemUserBinding
import id.allana.githubapp_bfaa.ui.detail.DetailUserFragmentDirections

class ListFollowerAdapter : RecyclerView.Adapter<ListFollowerAdapter.ListFollowerViewHolder>() {

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


    class ListFollowerViewHolder(private val binding: ListItemUserBinding): RecyclerView.ViewHolder(binding.root) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFollowerViewHolder {
        val binding = ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListFollowerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ListFollowerViewHolder, position: Int) {
        holder.bindView(listItem[position])
    }
}