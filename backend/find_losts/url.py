from django.urls import path
from . import views


urlpatterns = [
    path('lostobject/', views.LostObjectView.as_view(), name='lost_object'),
    path('lostitem/',views.LostItemView.as_view(), name='lost_item'),
    path('lostperson/', views.LostPersonView.as_view(), name='lost_object'),
    path('lostperson_image/',views.LostPersonImageView.as_view(), name='lost_item'),
    #path('lostobject/<int:id>/',views.LostItemView.as_view(), name='lost_item'),
    #path('lostcomb/', views.CombineListView.as_view(), name='lost_object'),
]