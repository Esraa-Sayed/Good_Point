from django.db import models

from find_losts.models import FoundItem
from user_account.models import User


# Create your models here.

class Notification(models.Model):
    title = models.CharField(max_length=30, null=False)
    description = models.CharField(max_length=100, null=True)
    date_time = models.DateTimeField(auto_now_add=True)
    is_sent = models.BooleanField(default=False)
    is_read = models.BooleanField(default=False)
    user_id = models.ForeignKey(User, related_name='notified', on_delete=models.CASCADE, db_column='user_id')

    class Meta:
        db_table = 'notification'
