package com.inhyuck.adlist.api

import com.inhyuck.adlist.db.entity.Ad
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root (name = "ads", strict = false)
data class GetAdsResponse (
    @field:ElementList(inline = true)
    var ads:ArrayList<Ad>? = null,

    @field:Element(name = "totalCampaignsRequested")
    var totalCampaignsRequested:Int? = null,

    @field:Element(name = "version")
    var version:String? = null
)