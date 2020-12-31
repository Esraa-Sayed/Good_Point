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


class MatchedNotification(models.Model):
    id = models.OneToOneField(Notification, primary_key=True, on_delete=models.CASCADE, db_column='id')
    user_id_matched = models.ForeignKey(User, on_delete=models.CASCADE, db_column='user_id_matched')

    class Meta:
        db_table = 'matched_notification'


class CandidateNotification(models.Model):
    id = models.OneToOneField(Notification, primary_key=True, on_delete=models.CASCADE, db_column='id')
    candidating_item = models.ForeignKey(FoundItem, on_delete=models.CASCADE, db_column='candidating_item')

    class Meta:
        db_table = 'candidate_notification'
