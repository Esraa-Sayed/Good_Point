from django.urls import path
from . import views
from user_account import views as user_view



urlpatterns = [
    path('lostobject/', views.LostObjectView.as_view(), name='lost_object'),
    path('lostitem/', views.LostItemView.as_view(), name='lost_item'),
    path('lostperson/', views.LostPersonView.as_view(), name='lost_object'),
    path('lostperson_image/', views.LostPersonImageView.as_view(), name='lost_item'),
    path('founditem/', views.FoundItemView.as_view(), name='lost_object'),
    path('foundobject/', views.FoundObjectView.as_view(), name='lost_item'),
    path('foundperson/', views.FoundPersonView.as_view(), name='lost_object'),
    path('foundperson_image/', views.FoundPersonImageView.as_view(), name='lost_item'),
    path('lostobject/<int:pk>/', views.LostObjectDetailsView.as_view(), name='lost_item'),
    path('lostitem/<int:pk>/', views.LostItemDetailsView.as_view(), name='lost_item'),
    path('lostperson/<int:pk>/', views.LostPersonDetailsView.as_view(), name='lost_item'),
    path('foundobject/<int:pk>/', views.FoundObjectDetalisView.as_view(), name='lost_item'),
    path('founditem/<int:pk>/', views.FoundItemDetailsView.as_view(), name='lost_item'),

    #path('comp_lostView/(?P<city>[\w.@+-]+)/$', views.comp_lostView),
    #path('Comp_ViewSet/<int:pk>/', views.Comp_ViewSet(), name='lost_item'),

    path('LostItem/', views.LostItemFilter.as_view(), name='LostObjectFilter'),
    path('LostItemFilter/', views.LostItemFilter.as_view(), name='LostItemFilter'),
    #path('user_id=<int:user_id>/', views.UserNotificationView.as_view(), name='notification'),
    #path('lostperson_image/<int:pk>/', views.LostPersonImageDetailsView.as_view(), name='lost_item'),
    #path('lostcomb/', views.CombineListView.as_view(), name='lost_object'),
    path('founder/<int:id>/', user_view.WhoFoundItemView.as_view(), name='who found item'),
    path('map/', views.MapView.as_view(), name='map'),
]