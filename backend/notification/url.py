from django.urls import path
from . import views

urlpatterns = [
    path('', views.NotificationView.as_view(), name='notification'),
    path('user_id=<int:user_id>/', views.UserNotificationView.as_view(), name='notification'),
]