from django.urls import path
from . import views


urlpatterns = [
    path('lostobject/', views.LostObjectView.as_view(), name='lost_object'),
    path('lostitem/',views.LostItemView.as_view(), name='lost_item'),
    path('lostperson/', views.LostPersonView.as_view(), name='lost_object'),
    path('lostperson_image/',views.LostPersonImageView.as_view(), name='lost_item'),
    path('founditem/', views.FoundItemView.as_view(), name='lost_object'),
    path('foundobject/',views.FoundObjectView.as_view(), name='lost_item'),
    path('foundperson/', views.FoundPersonView.as_view(), name='lost_object'),
    path('foundperson_image/',views.FoundPersonImageView.as_view(), name='lost_item'),
    path('lostobject/<int:pk>/',views.LostObjectDetailsView.as_view(), name='lost_item'),
    path('lostitem/<int:pk>/',views.LostItemDetailsView.as_view(), name='lost_item'),
    #path('lostcomb/', views.CombineListView.as_view(), name='lost_object'),
]