from django.db import models
from user_account.models import User


# Create your models here.

class Notification(models.Model):
    title = models.CharField(max_length=30, null=False)
    description = models.CharField(max_length=100, null=True)
    date_time = models.DateTimeField(auto_now_add=True)
    is_read = models.BooleanField(default=False)
    is_archived = models.BooleanField(default=False)
    user_id = models.ForeignKey(User, related_name='notified', on_delete=models.DO_NOTHING, db_column='user_id')
