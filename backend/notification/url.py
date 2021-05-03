from django.urls import path
from . import views

urlpatterns = [
    path('noti/', views.NotificationView.as_view(), name='notification'),

]