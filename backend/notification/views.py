from django.shortcuts import render
from rest_framework import generics
from .models import Notification
from .serializers import NotificationSerializer

# Create your views here.
class NotificationView(generics.ListCreateAPIView):
    queryset = Notification.objects.all()
    serializer_class = NotificationSerializer


class UserNotificationView(generics.ListAPIView):
    serializer_class = NotificationSerializer

    def get_queryset(self):
        notifications = self.kwargs['user_id']
        return Notification.objects.filter(user_id=notifications)
