package id.allana.githubapp_bfaa.data.model.network

import com.google.gson.annotations.SerializedName

data class ResponseSearchUser(

	@field:SerializedName("total_count")
	val totalCount: Int? = null,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean? = null,

	@field:SerializedName("items")
	val items: List<ItemsItem>? = null
)

data class ItemsItem(

	@field:SerializedName("login")
	val username: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("html_url")
	val urlProfile: String? = null

)
